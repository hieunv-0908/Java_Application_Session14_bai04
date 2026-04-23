package re.java_application_session14_bai04.service;

import jakarta.transaction.Transaction;
import org.hibernate.Session;

public class CheckoutService {
    public void reserveProduct(Long userId, Long productId, int quantity) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Product product = session.get(Product.class, productId);

            if (product == null) {
                throw new RuntimeException("Sản phẩm không tồn tại");
            }

            if (product.getStock() < quantity) {
                throw new RuntimeException("Không đủ hàng");
            }

            // Trừ kho tạm thời
            product.setStock(product.getStock() - quantity);
            session.update(product);

            // Tạo order pending
            Order order = new Order();
            order.setUserId(userId);
            order.setProductId(productId);
            order.setQuantity(quantity);
            order.setStatus("PENDING");
            order.setExpireTime(System.currentTimeMillis() + 15 * 60 * 1000);

            session.save(order);

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}
