package cn.pompip.browser.util.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.json.JsonParserFactory;

import java.io.IOException;


public class BrowserJsonSerializer extends JsonSerializer<String> {
	@Override
	public void serialize(String str, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeObject(JsonParserFactory.getJsonParser().parseList(str));
	}
}
