package com.daveayan.scalingwinner.greeting.translator;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/translate")
public class TranslatorController {
    private static final Logger LOG = LoggerFactory.getLogger(TranslatorController.class);

    private Map<String, String> translations = new HashMap<String, String>();

    public TranslatorController() {
        LOG.trace("IN TranslatorController constructor");
        
        translations.put("Hello", "Hola");
        translations.put("Thank You", "Gracias");
        translations.put("Bye", "Adios");

        LOG.trace("OUT TranslatorController constructor");
    }

    @GetMapping("")
    ResponseEntity<GreetingTranslator> getTranslatedString(
        @RequestHeader HttpHeaders headers, 
        @RequestParam String text,
        @RequestParam String from,
        @RequestParam String to
    ) {
        LOG.trace("IN getTranslatedString");
        String translatedText = text;
        String status = "Not Found";
        if(translations.containsKey(text)) {
            translatedText = translations.get(text);
            status = "Found";
        }
        GreetingTranslator t = new GreetingTranslator(text, translatedText, from, to, status);

        LOG.trace("OUT getTranslatedString " + t);
        return new ResponseEntity<GreetingTranslator>(t, HttpStatus.OK);
    }
}
