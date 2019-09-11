package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工人表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-08-22 14:44:11
 */
@Data
@TableName("worker")
public class WorkerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 工人ID
	 */
	private Integer userId;
	/**
	 * 打工开始时间(时间戳/秒)
	 */
	private Integer partTime;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 父ID
	 */
	private Integer parentId;

}
