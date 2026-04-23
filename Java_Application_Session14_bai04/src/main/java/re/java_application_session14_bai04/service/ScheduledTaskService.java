package re.java_application_session14_bai04.service;

import org.hibernate.Session;

public class ScheduledTaskService {
    public void releaseExpiredOrders() {
        Session session = HibernateUtils.getSessionFactory().openSession();

        try {
            List<Order> orders = session.createQuery(
                    "FROM Order WHERE status = 'PENDING' AND expireTime < :now",
                    Order.class
            ).setParameter("now", System.currentTimeMillis()).list();

            for (Order order : orders) {
                Transaction tx = session.beginTransaction();

                try {
                    Product product = session.get(Product.class, order.getProductId());

                    if (product != null) {
                        product.setStock(product.getStock() + order.getQuantity());
                        session.update(product);
                    }

                    order.setStatus("CANCELLED");
                    session.update(order);

                    tx.commit();

                } catch (Exception e) {
                    tx.rollback();
                }
            }

        } finally {
            session.close();
        }
    }
}
