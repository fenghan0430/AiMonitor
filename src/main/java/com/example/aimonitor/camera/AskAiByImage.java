package com.example.aimonitor.camera;

import com.example.aimonitor.entity.MonitorStatus;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AskAiByImage {

    @Value("${llm.token}")
    private static String token;
    @Value("${llm.baseurl}")
    private static String baseurl;
    @Value("${llm.model}")
    private static String model;
    
    public static void main(String[] args) {
        String apiKey = "sk-oxZeeVlEmO4O9I8ID7027f6dF9E74aE8Af987f624f33D1D7"; // 替换为您的OpenAI API密钥
        String url = "http://47.119.174.245:3000/v1/chat/completions";
        String question = loadFileContentAsString("p.md");
        String imagePath = "open2.png"; // 替换为本地图片路径

        String response = getModelResponse(apiKey, url, question, imagePath);
        System.out.println(response);

        JSONObject result = extractNestedJson(response);
        String typec = (String) result.get("status");
        System.out.println(typec);
    }

    /**
     * 调用模型接口，传入问题和图片路径，返回结果。
     * @param apiKey OpenAI API密钥
     * @param url 接口URL
     * @param question 用户的问题
     * @param imagePath 图片路径
     * @return 模型返回的结果
     */
    public static String getModelResponse(String apiKey, String url, String question, String imagePath) {
        String base64EncodedImage = encodeImageToBase64(imagePath);

        if (base64EncodedImage == null) {
            return "Failed to encode image to Base64.";
        }

        // 创建 JSON 请求体
        JSONObject contentText = new JSONObject();
        contentText.put("type", "text");
        contentText.put("text", question);

        JSONObject contentImage = new JSONObject();
        contentImage.put("type", "image_url");
        JSONObject imageUrl = new JSONObject();
        imageUrl.put("url", base64EncodedImage);
        contentImage.put("image_url", imageUrl);

        JSONArray contentArray = new JSONArray();
        contentArray.put(contentText);
        contentArray.put(contentImage);

        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", contentArray);

        JSONArray messages = new JSONArray();
        messages.put(message);

        JSONObject requestBodyJson = new JSONObject();
        if (model == null){
            System.out.println("SpringBoot示例没有启动！模型使用请在代码中手动指定。");
            requestBodyJson.put("model", "glm-4v-plus");
        }else {
            requestBodyJson.put("model", model);
        }
        requestBodyJson.put("messages", messages);
        requestBodyJson.put("max_tokens", 300);

        // 配置 OkHttp 客户端
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody body = RequestBody.create(
                requestBodyJson.toString(),
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Error: " + response.code() + " - " + response.body().string();
            }
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * 将图片文件转换为Base64编码
     * @param imagePath 图片路径
     * @return Base64编码字符串
     */
    private static String encodeImageToBase64(String imagePath) {
        try {
            File file = new File(imagePath);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            System.err.println("Error reading the image file: " + e.getMessage());
            return null;
        }
    }

    /**
     * 从文件中加载内容为字符串
     * @param filePath 文件路径
     * @return 文件内容的字符串表示
     */
    public static String loadFileContentAsString(String filePath) {
        try {
            File file = new File(filePath);
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return null;
        }
    }

    /**
     * 从模型输出中提取嵌套的 JSON 数据。
     *
     * @param modelOutput 模型返回的 JSON 字符串
     * @return 解析后的 JSON 数据，或者错误提示
     */
    public static JSONObject extractNestedJson(String modelOutput) {
        JSONObject result = new JSONObject();
        try {
            // 将模型输出解析为 JSON 对象
            JSONObject responseJson = new JSONObject(modelOutput);

            // 提取 choices 数组的第一个元素的 message.content
            String nestedContent = responseJson
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            // 将嵌套的内容字符串解析为 JSON 对象
            JSONObject nestedJson = new JSONObject(nestedContent);

            // 返回嵌套的 JSON 数据
            result.put("status", "success");
            result.put("data", nestedJson);
        } catch (Exception e) {
            // 捕获解析错误并返回错误信息
            result.put("status", "error");
            result.put("message", "解析嵌套 JSON 时发生错误: " + e.getMessage());
        }

        return result;
    }

    public static MonitorStatus CheckMode(){
        MonitorStatus monitorStatus = new MonitorStatus();
        monitorStatus.setTime(LocalDateTime.now().toString());

        String question = loadFileContentAsString("p.md");
        String imagePath = "now.jpg";
        String req = getModelResponse(token, baseurl, question, imagePath);

        JSONObject jsonObject = extractNestedJson(req);
        if (jsonObject.getString("status").equals("success")) {
            JSONObject data = jsonObject.getJSONObject("data");
            int type = data.getInt("status");
            if (type == 1) {
                monitorStatus.setStatus(true);
            }else {
                monitorStatus.setStatus(false);
            }
            monitorStatus.setMessage(data.getString("message"));
            return monitorStatus;
        }
        return monitorStatus;
    }
}
