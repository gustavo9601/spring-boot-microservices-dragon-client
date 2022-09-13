package springbootmicroservicesclientdragon.springbootmicroservicesclientdragon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import zipkin2.internal.Trace;

import java.time.LocalDateTime;

@Service
public class FooServiceZipkin {

    @Autowired
    private Tracer tracer;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(FooServiceZipkin.class);


    public void printLog(){
        logger.info("Ejecutando printLog a las :\t"+ LocalDateTime.now());

        Span newSpan = this.tracer.nextSpan().name("newSpan");
        // De esta forma crea un nuevo span, creando como una jerarquia para visualizarlo desde zipkin
        try(Tracer.SpanInScope ws = this.tracer.withSpan(newSpan.start())) {
            logger.info("Ejecutando printLog dentro del newSpan a las :\t" + LocalDateTime.now());
        }finally {
            newSpan.end();
        }

    }

}
