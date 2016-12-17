package com.barath.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.StdConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringXssFilterSampleApplicationTests {

	@Test
	public void test() throws Exception {
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("NAME1","<script>alert('hello')</script>BARATH");
		map.put("NAME2","PARI");
		map.put("NAMES",Arrays.asList("MEGHNA","BALA","RAVIE"));
		Response response=new Response();
		response.setOutput(map);
		
		ObjectMapper mapper=new ObjectMapper();
		//mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,true);
		//mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE,true);
		mapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
		String responseString=mapper.writeValueAsString(response);		
		System.out.println("responseString "+responseString);
		//SimpleModule module = new SimpleModule();
		//module.addDeserializer(Response.class, new ResponseDeserializer());
		//mapper.registerModule(module);
		
		//ResponseWrapper validResponse=mapper.readValue(responseString.getBytes(), ResponseWrapper.class);
		Response validResponse=mapper.readValue(responseString.getBytes(), Response.class);
		//System.out.println("validResponse "+validResponse.getResponse().toString());
		System.out.println("validResponse "+validResponse.toString());
		
		
	}
	
	protected static class ResponseDeserializer extends StdDeserializer<Response> { 
		private static final ObjectMapper mapper = new ObjectMapper();
		 
	    public ResponseDeserializer() { 
	        this(null); 
	    } 
	 
	    public ResponseDeserializer(Class<?> vc) { 
	        super(vc); 
	    }
	 
	    @Override
	    public Response deserialize(JsonParser jp, DeserializationContext ctxt) 
	      throws IOException, JsonProcessingException {
	    	
	    	
	    	return mapper.readValue(jp.getText(), Response.class);
	    	
	      /*  JsonNode node = jp.getCodec().readTree(jp);
	        Map<String,Object> map=new HashMap<String,Object>();
	        Iterator<JsonNode> iterator=node.elements();
	        while(iterator.hasNext()){
	        	JsonNode elementNode=iterator.next();
	        	Iterator<String> fieldNames=elementNode.fieldNames();
	        	ObjectMapper mapper=new ObjectMapper();
	        	while(fieldNames.hasNext()){
	        		String mapString=mapper.writeValueAsString(elementNode.get(fieldNames.next()));
	        		String responseString=new String(new SerializedString(mapString).asQuotedUTF8()).toString();
	        		System.out.println("response string "+HtmlUtils.htmlEscape(responseString));
	        		map.put(fieldNames.next(),responseString );
	        	}*/
	        	
	        	/*System.out.println("elementNode " +elementNode);
	        	
	        	while(fieldNames.hasNext()){
	        		System.out.println("field name "+fieldNames.next()+" field value "+ elementNode.get(fieldNames.next()));
	        		map.put(fieldNames.next(), elementNode.get(fieldNames.next()));
	        	}
	        	
	        	
	        }
	       
	 
	       return new Response(map);*/
	    }
	}
	
	protected static class ResponseWrapper {
		
	    public Response response;

		public Response getResponse() {
			return response;
		}

		public void setResponse(Response response) {
			this.response = response;
		}

		@Override
		public String toString() {
			return "ResponseWrapper [response=" + response + "]";
		}
	    
	    
	}
	
	 public static class HTMLConverter extends StdConverter<String, String> {
	        public String convert(String value) {
	        	System.out.println("decoder");
	            return value==null ? null : value.toUpperCase();
	        }
	    }

	
	protected static class HTMLCharacterEscapes extends CharacterEscapes
	{
	    private final int[] asciiEscapes;
	    
	    public HTMLCharacterEscapes()
	    {
	        // start with set of characters known to require escaping (double-quote, backslash etc)
	        int[] esc = CharacterEscapes.standardAsciiEscapesForJSON();
	        // and force escaping of a few others:
	        esc['<'] = CharacterEscapes.ESCAPE_CUSTOM;
	        esc['>'] = CharacterEscapes.ESCAPE_CUSTOM;
	        esc['&'] = CharacterEscapes.ESCAPE_CUSTOM;
	        esc['\''] = CharacterEscapes.ESCAPE_STANDARD;
	        asciiEscapes = esc;
	    }
	    // this method gets called for character codes 0 - 127
	    @Override public int[] getEscapeCodesForAscii() {
	    	System.out.println("get escape "+asciiEscapes.toString());
	        return asciiEscapes;
	    }
	    // and this for others; we don't need anything special here
	    @Override public SerializableString getEscapeSequence(int ch) {
	        // no further escaping (beyond ASCII chars) needed:
	    	System.out.println("get escape "+ch);
	    	switch(ch) {
			case '<':
				return new SerializedString("&gt");
			case '>':
				return new SerializedString("&lt");
			case '&':
				return new SerializedString("&amp");
					
			default: return null;
				
			}
	        
	    }
	}
	
	protected static class Response{
		
		private Object output;

		public Object getOutput() {
			return output;
		}

		public void setOutput(Object output) {
			this.output = output;
		}

		@Override
		public String toString() {
			return "Response [output=" + output + "]";
		}

		public Response(Object output) {
			super();
			this.output = output;
		}

		public Response() {
			super();
			
		}
		
		
		
		
	}

}
