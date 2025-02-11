package co.kr.nomadlab.springgdic.util;

import co.kr.nomadlab.springgdic.exception.CustomException;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.http.HttpStatus;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;

public class Base64Decoded implements MultipartFile {
//    public static MultipartFile convertBase64ToMultipartFile(String base64)
//            throws IOException {
//        byte[] decodedData;
//        try {
////            String[] parts = base64.split("/");
////            // base64Data : data:image/png;base64, 없앤 base64 String 값
////            String base64Data = parts[1];
//            // 하드 디스크는 이진데이터를 읽어 저장하므로 base64 문자셋 -> 이진 데이터 디코딩
////            decodedData = Base64.getDecoder().decode(base64);
//             byte[] decodedBytes = Base64.getDecoder().decode(base64);
//
//        } catch (Exception e) {
//            throw new CustomException("코트 프로필 사진이 올바른 base64 문자열이 아닙니다.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new Base64Decoded(decodedData);
//    }
    public static String base64Decoder(String base64String) {
//        String base64String = "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC";

        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        // 디코딩된 이진 데이터를 원하는 방식으로 처리할 수 있습니다.
        // 예를 들어, 파일로 저장하거나 다른 용도로 사용할 수 있습니다.

        // 예시: 디코딩된 데이터를 파일로 저장
        try (FileOutputStream fos = new FileOutputStream("decoded_image.jpg")) {
            fos.write(decodedBytes);
        } catch (IOException e) {
            // 파일 저장 중에 예외가 발생한 경우에 대한 처리
            e.printStackTrace();
        }
        return Arrays.toString(decodedBytes);
    }

    private final byte[] imgContent;

    public Base64Decoded(byte[] imgContent) {
        this.imgContent = imgContent;
    }

    @Override
    public String getName() {
        return "base64.jpg";
    }

    @Override
    public String getOriginalFilename() {
        return "base64.jpg";
    }

    @Override
    public String getContentType() {
        return "image/jpeg";
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        new FileOutputStream(file).write(imgContent);
    }
}