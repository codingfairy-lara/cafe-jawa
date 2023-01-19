package cafe.jawa.product.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cafe.jawa.product.model.dto.Attachment;
import cafe.jawa.product.model.dto.Product;
import cafe.jawa.product.model.exception.ProductException;

public class ProductDao {
	private Properties prop = new Properties();
	
	public ProductDao() {
		String path = ProductDao.class.getResource("/sql/product/product-query.properties").getPath();
		try {
			prop.load(new FileReader(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Product> selectProductList(Connection conn) {
		String sql = prop.getProperty("selectProductList");
		List<Product> productList = new ArrayList<>();
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			try(ResultSet rset = pstmt.executeQuery()){
				while(rset.next()) {
					Product product = handleProductResultSet(rset);
					productList.add(product);					
				}
			}
		} catch (SQLException e) {
			throw new ProductException("상품 목록 조회 오류!", e);
		}
		return productList;
	}

	private Product handleProductResultSet(ResultSet rset) throws SQLException {
		Product product = new Product();
		product.setProductId(rset.getInt("id"));
		product.setSubCategory(rset.getString("sub_category_id"));
		product.setProductName(rset.getString("name"));
		product.setDescription(rset.getString("describe"));
		product.setPrice(rset.getInt("unit_price"));
		product.setEnrollDate(rset.getTimestamp("enroll_date"));
		return product;
	}

	public List<Attachment> selectAttachmentList(Connection conn) {
		String sql = prop.getProperty("selectProductImageList"); // select * from product_images
		List<Attachment> attachmentList = new ArrayList<>();
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			try(ResultSet rset = pstmt.executeQuery()){
				while(rset.next()) {
					Attachment attach = handleAttachmentResultSet(rset);
					attachmentList.add(attach);
				}
			}
		} catch (Exception e) {
			throw new ProductException("상품 이미지 조회 오류!", e);
		}
		return attachmentList;
	}
	
//	public List<Attachment> selectAttachmentList(Connection conn, int productId) {
//		String sql = prop.getProperty("selectProductImages"); // select * from product_images where product_id = ?
//		List<Attachment> attachmentList = new ArrayList<>();
//		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
//			pstmt.setInt(1, productId);
//			
//			try(ResultSet rset = pstmt.executeQuery()){
//				while(rset.next()) {
//					Attachment attach = handleAttachmentResultSet(rset);
//					attachmentList.add(attach);
//				}
//			}
//		} catch (Exception e) {
//			throw new ProductException("상품 이미지 조회 오류!", e);
//		}
//		return attachmentList;
//	}

	private Attachment handleAttachmentResultSet(ResultSet rset) throws SQLException {
		Attachment attach = new Attachment();
		attach.setId(rset.getInt("id"));
		attach.setProductId(rset.getInt("product_id"));
		attach.setOriginalFilename(rset.getString("original_filename"));
		attach.setRenamedFilename(rset.getString("renamed_filename"));
		attach.setEnrollDate(rset.getDate("enroll_date"));
		return attach;
	}
	
}
