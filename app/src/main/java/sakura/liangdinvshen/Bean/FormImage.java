package sakura.liangdinvshen.Bean;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/21
 * 功能描述：
 */
public class FormImage {

    // 参数的名称
    private String mName;

    // 文件名
    private String mFileName;

    // 文件的 mime，需要根据文档查询
    private String mMime;

    // 需要上传的图片资源，因为这里测试为了方便起见，直接把 bitmap 传进来，真正在项目中一般不会这般做，
    // 而是把图片的路径传过来，在这里对图片进行二进制转换
    private Bitmap mBitmap = null;

    public FormImage(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public String getName() {
        return "file";
    }

    public String getFileName() {
        return "add.png";
    }

    // 对图片进行二进制转换
    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 2, bos);
        return bos.toByteArray();
    }

    // 因为我知道是 png 文件，所以直接根据文档查的
    public String getMime() {
        return "image/png";
    }
}