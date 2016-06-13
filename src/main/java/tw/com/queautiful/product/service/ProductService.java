package tw.com.queautiful.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.queautiful.product.dao.ProductDao;
import tw.com.queautiful.product.entity.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao dao;

	public Product getById(Long productId) {
		return dao.findOne(productId);
	}

	public List<Product> getAll() {
		return dao.findAll();
	}

	public void insert(Product product) {
		dao.save(product);
	}

	public void update(Product product) {
		dao.save(product);
	}

	public void delete(Long productId) {
		dao.delete(productId);
	}
}
