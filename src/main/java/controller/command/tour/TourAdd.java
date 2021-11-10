package controller.command.tour;

import controller.command.Command;
import controller.dto.TourDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import model.entity.enums.HotelType;
import model.entity.enums.TourType;
import model.service.TourService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class TourAdd implements Command {
    public static final Logger log = LogManager.getLogger();
    private final TourService tourService;

    public TourAdd(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String lang = (String) request.getSession().getAttribute("lang");
        Locale ourLocale;
        if (lang == null) {
            ourLocale = Locale.getDefault();
        } else {
            ourLocale = Locale.forLanguageTag(lang);
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", ourLocale);

        String name = request.getParameter("name");
        String tourType = request.getParameter("tourType");
        String hotelType = request.getParameter("hotelType");
        String groupSize = request.getParameter("groupSize");
        String price = request.getParameter("price");

        if (name == null
                && tourType == null
                && hotelType == null
                && groupSize == null
                && price == null) {
            request.setAttribute("tourTypes", TourType.values());
            request.setAttribute("hotelTypes", HotelType.values());
            return "/tour-add.jsp";
        }

        TourDTO tourDTO = TourDTO.builder()
                .name(name)
                .tourType(TourType.valueOf(tourType))
                .hotelType(HotelType.valueOf(hotelType))
                .groupSize(Integer.parseInt(groupSize))
                .price(Double.parseDouble(price))
                .build();

        final Validator VALIDATOR =
                Validation.byDefaultProvider()
                        .configure()
                        .messageInterpolator(new ResourceBundleMessageInterpolator(
                                new AggregateResourceBundleLocator(Arrays.asList("messages"))))
                        .buildValidatorFactory()
                        .getValidator();

        Set<ConstraintViolation<TourDTO>> violations = VALIDATOR.validate(tourDTO);

        if (violations.isEmpty()) {
            tourService.addTour(tourDTO);
            log.info("tour created");
            return "redirect:/tours";
        } else {
            request.setAttribute("errors", violations);
            return "/tour-add.jsp";
        }

    }
}
