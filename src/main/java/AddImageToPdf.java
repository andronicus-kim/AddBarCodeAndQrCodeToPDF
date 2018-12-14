import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddImageToPdf {
    public static final String SRC = "C:\\Users\\andronicus\\IdeaProjects\\QrcodeTest\\src\\main\\resources\\pdfs\\cert.pdf";
    //public static final String DEST = "results/stamper/hello_with_image_id.pdf";
    public static final String DEST = "C:\\Users\\andronicus\\IdeaProjects\\QrcodeTest\\src\\main\\resources\\pdfs\\result.pdf";
    public static final String IMG = "C:\\Users\\andronicus\\IdeaProjects\\QrcodeTest\\src\\main\\resources\\images\\qrcode.png";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddImageToPdf().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        Image image = Image.getInstance(IMG);
        image.scalePercent(30);
        PdfImage stream = new PdfImage(image, "", null);
        stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
        PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
        image.setDirectReference(ref.getIndirectReference());
        image.setAbsolutePosition(120, 35);
        PdfContentByte over = stamper.getOverContent(1);
        over.addImage(image);
        stamper.close();
        reader.close();
    }
}
