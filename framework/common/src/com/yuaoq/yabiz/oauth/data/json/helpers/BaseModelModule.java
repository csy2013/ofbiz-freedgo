package com.yuaoq.yabiz.oauth.data.json.helpers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.net.MediaType;
import com.yuaoq.yabiz.oauth.result.WebResult;
import org.bson.types.ObjectId;

import java.io.IOException;


public final class BaseModelModule extends SimpleModule
{
	private static final long serialVersionUID = 1L;

	public BaseModelModule()
	{
//		addDeserializer(ObjectId.class, new ObjectIdDeserializer());
		addSerializer(ObjectId.class, new ObjectIdSerializer());
		addSerializer(Enum.class, new EnumSerializer());
		addSerializer(MediaType.class, new MediaTypeSerializer());

		// https://github.com/FasterXML/jackson-databind/issues/274
		setMixInAnnotation(WebResult.class, WebResultMixIn.class);
	}


	public class ObjectIdSerializer extends JsonSerializer<ObjectId>
	{
		@Override
		public void serialize(ObjectId value, JsonGenerator generator, SerializerProvider provider) throws IOException {
			if(value != null){
				generator.writeString(value.toString());
			}
		}
	}


	@SuppressWarnings("rawtypes")
	public class EnumSerializer extends JsonSerializer<Enum>
	{
		@Override
		public void serialize(Enum value, JsonGenerator generator, SerializerProvider provider) throws IOException {
			if(value != null){
				generator.writeString(value.toString());
			}
		}
	}


	public class MediaTypeSerializer extends JsonSerializer<MediaType>
	{
		@Override
		public void serialize(MediaType value, JsonGenerator generator, SerializerProvider provider) throws IOException {
			if(value != null){
				generator.writeString(value.toString());
			}
		}
	}

	
	public class WebResultMixIn
	{
		@JsonProperty("success")
        String success;

		@JsonProperty("returndata")
        String returnData;

		@JsonProperty("status")
        String statusArchive;

		@JsonProperty("results")
        String resultList;

		@JsonProperty("statuscode")
        String statusCode;
		
//		@JsonProperty("parameters")
		@JsonIgnore
String dataStore;
	}
}
