package top.longmarch.douyin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("douyin_account")
public class DouyinAccount {

    @TableId
    private String openId;
    private String unionId;
    private String nickname;
    private String source;
    private String location;
    private String avatar;
    private String avatarLarger;
    private Integer gender;
    private String genderStr;
    private String country;
    private String province;
    private String city;
    private String eAccountRole;
    private String scope;
    private Integer expireIn;
    private String accessToken;
    private String refreshToken;
    private Integer fansNum;
    private Integer followingNum;
    private Integer likeNum;
    private Integer commentNum;
    private Integer shareNum;
    private Integer profileNum;
    private Integer videoNum;
    private Integer videoPlayNum;

}