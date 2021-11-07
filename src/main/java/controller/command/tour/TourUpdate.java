package controller.command.tour;


import controller.command.Command;
import controller.dto.TourDTO;
import exception.DaoException;
import model.entity.Tour;
import model.entity.enums.HotelType;
import model.entity.enums.TourType;
import model.service.TourService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class TourUpdate implements Command {
    public static final Logger log = LogManager.getLogger();

    private final TourService tourService;

    public TourUpdate(TourService tourService) {
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

        long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
            tourService.findTourById(id);
        } catch (NumberFormatException e) {
            return "/404.jsp";
        } catch (IllegalAccessError e) {
            return "/500.jsp";
        }

        log.info("trying to update tour");

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
            Tour tour = tourService.findTourById(id);
            request.setAttribute("tour", tour);
            request.setAttribute("tourTypes", TourType.values());
            request.setAttribute("hotelTypes", HotelType.values());
            return "/tour-update.jsp";
        }

        TourDTO tourDTO = TourDTO.builder()
                .name(name)
                .tourType(TourType.valueOf(tourType))
                .hotelType(HotelType.valueOf(hotelType))
                .groupSize(Integer.parseInt(groupSize))
                .price(Double.parseDouble(price))
                .build();

        log.info("tour almost updated");

        tourService.updateTour(id, tourDTO);

        return "redirect:/tours";
    }
}