package model.dao;

import model.dao.implementation.JDBCDaoFactory;

public abstract class DaoFactory {
    private static  DaoFactory daoFactory;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }

    public abstract UserDao createUserDao(DaoConnection connection);
    public abstract TourDao createTourDao(DaoConnection connection);
    public abstract OrderDao createOrderDao(DaoConnection connection);

    public abstract DaoConnection getConnection();
}
