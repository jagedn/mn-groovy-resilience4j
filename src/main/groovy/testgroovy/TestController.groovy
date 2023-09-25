package testgroovy

import groovy.transform.CompileStatic
import io.github.resilience4j.micronaut.annotation.Bulkhead
import io.micronaut.context.annotation.Executable
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.scheduling.TaskExecutors
import jakarta.inject.Inject
import jakarta.inject.Named
import reactor.core.publisher.Mono

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

@CompileStatic
@Controller
class TestController {

    @Inject @Named(TaskExecutors.IO) ExecutorService executor

    @Bulkhead(name="test", fallBackMethod="fallBack")
    @Get
    CompletableFuture<String> test(){
        CompletableFuture.supplyAsync({
            sleep 13000
            "${new Date()}".toString()
        }, executor)
    }

    @Executable
    CompletableFuture<String> fallBack(){        
        CompletableFuture.completedFuture("pos algo ha ido mal")
    }
}
