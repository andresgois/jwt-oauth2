package br.com.andresgois.FeignApplication.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class KeyConverterConfig {
}

@Component
@ConfigurationPropertiesBinding
class PrivateKeyConverter implements Converter<String, RSAPrivateKey> {

    private static final ResourceLoader resourceLoader = new DefaultResourceLoader();
    @Override
    public RSAPrivateKey convert(String location) {
        try (InputStream is = resourceLoader.getResource(location).getInputStream()) {
            return RsaKeyConverters.pkcs8().convert(is);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

@Component
@ConfigurationPropertiesBinding
class PublicKeyConverter implements Converter<String, RSAPublicKey> {
    private static final ResourceLoader resourceLoader = new DefaultResourceLoader();
    @Override
    public RSAPublicKey convert(String location) {
        try (InputStream is = resourceLoader.getResource(location).getInputStream()) {
            return RsaKeyConverters.x509().convert(is);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
