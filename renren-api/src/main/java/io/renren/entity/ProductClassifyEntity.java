package io.renren.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品分类
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 09:47:13
 */
@Data
@TableName("product_classify")
public class ProductClassifyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 0隐藏 1显示
	 */
	private Integer state;
	/**
	 * 列表数据
	 */
	@TableField(exist = false)
	private List<?> list;

}
