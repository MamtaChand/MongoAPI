package np.com.softwarica.mongoapi;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HeroAPI {
    @POST("heroes")
    Call<Void> addHero (@Header("Cookie") String  cookie, @Body Hero hero);

    @GET("heroes")
    Call<ArrayList<Hero>> getHeroes(@Header("Cookie") String  cookie);

    @FormUrlEncoded
    @POST("heroes")
    Call<Void>addHero(@Header("Cookie") String  cookie,@Field("name") String name,@Field("desc")String desc);

    @FormUrlEncoded
    @POST("heroes")
    Call<Void>addHero(@Header("Cookie") String  cookie,@FieldMap Map<String,String> map);

    @Multipart
    @POST("upload")
    Call<ImageResponse>uploadImage(@Header("Cookie") String  cookie,@Part MultipartBody.Part img);

    @FormUrlEncoded
    @POST("users/login")
    Call<LoginSignUpResponse>checkUser(@Field("username") String username, @Field("password") String password);
}
