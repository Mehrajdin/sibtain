package com.sibtain.security.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleGrantedAuthorityDeserializer extends StdDeserializer<List<SimpleGrantedAuthority>> {
    public SimpleGrantedAuthorityDeserializer() {
        super(SimpleGrantedAuthority.class);
    }
    private static  final Logger logger = LoggerFactory.getLogger(SimpleGrantedAuthorityDeserializer.class);
    @Override
    public List<SimpleGrantedAuthority> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        logger.info("Custom serializer : deserialize Type SimpleGrantedAuthority List");
        JsonNode tree = p.getCodec().readTree(p);
        List<SimpleGrantedAuthority> mapedNodes = new ArrayList<>();
        if (tree != null) {
            for (JsonNode jsonNode : tree) {
                mapedNodes.add(
                        new SimpleGrantedAuthority(jsonNode.get("authority").textValue()));
            }
        }
        return mapedNodes;
    }
}
