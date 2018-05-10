package com.pol.gestionart;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import com.pol.gestionart.converters.FamiliaConverter;
import com.pol.gestionart.converters.ProductoConverter;
import com.pol.gestionart.converters.VentaCabeceraConverter;

@Configuration
public class ConversionConfiguration {

	@Bean(name="conversionService")
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();
        ConversionService object = bean.getObject();
        return object;
    }

    private Set<Converter> getConverters() {
        Set<Converter> converters = new HashSet<Converter>();

        converters.add(new FamiliaConverter());
        converters.add(new ProductoConverter());
        converters.add(new VentaCabeceraConverter());

        return converters;
    }
}
