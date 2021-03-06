package tw.com.softleader.eeit8600.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.softleader.eeit8600.product.dao.BrandDao;
import tw.com.softleader.eeit8600.product.entity.Brand;

@Service
public class BrandService {

	@Autowired
	private BrandDao brandDao;
	
	public Brand getById(Long brandId){
		return brandDao.findOne(brandId);
	}
	
	public List<Brand> getAll(){
		return brandDao.findAll();	
	}
	
	public void insert(Brand brand){
		brandDao.save(brand);
	}
	
	public void update(Brand brand){
		brandDao.save(brand);
	}
	
	public void delete(Long brandId){
		brandDao.delete(brandId);
	}
}
