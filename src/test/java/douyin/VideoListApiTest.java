/*
 * 
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package douyin;

import cn.hutool.http.HttpUtil;
import com.douyin.open.api.VideoListApi;
import com.douyin.open.models.VideoListVideoListInlineResponse200;
import org.junit.Ignore;
import org.junit.Test;

/**
 * API tests for VideoListApi
 */
@Ignore
public class VideoListApiTest {

    private final VideoListApi api = new VideoListApi();

    /**
     * 查询授权账号视频数据
     *
     * * Scope: &#x60;video.list&#x60; 
     *
     *          if the Api call fails
     */
    @Test
    public void videoListGetTest() {
        String openId = "b1f9c170-d2d8-4d06-b02d-dd20027bfcf0";
        String accessToken = "act.1c926119d77da82c90ee67c7bd62bf09pdRBAjSfx4T30lNuyQN1GXy4yWc2";
        Integer count = 10;
        Long cursor = 0L;
        VideoListVideoListInlineResponse200 response = api.videoListGet(openId, accessToken, count, cursor);
        System.out.println(response.getData().getList());
        // TODO: test validations
    }

    @Test
    public void videoListGetTest2() {
        String openId = "b1f9c170-d2d8-4d06-b02d-dd20027bfcf0";
        String accessToken = "act.1c926119d77da82c90ee67c7bd62bf09pdRBAjSfx4T30lNuyQN1GXy4yWc2";
        Integer count = 10;
        Integer cursor = 1;
        String url = "https://open.douyin.com/video/list?open_id=%s&access_token=%s&count=%s&cursor=%s";
        String format = String.format(url, openId, accessToken, count, cursor);
        System.out.println(format);
        String s = HttpUtil.get(format);
        System.out.println(s);
        // TODO: test validations
    }

}
