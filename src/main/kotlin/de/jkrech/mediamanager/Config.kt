package de.jkrech.mediamanager

import de.jkrech.mediamanager.application.BookAggregate
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.axonframework.eventsourcing.EventSourcingRepository
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.modelling.command.Repository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.sql.SQLException

@Configuration
open class Config {
    
    @Bean
    open fun commandBus() : SimpleCommandBus {
        return SimpleCommandBus.builder().build();
    }

    @Bean
    open fun commandGateway() : DefaultCommandGateway {
        return DefaultCommandGateway.builder().commandBus(commandBus()).build();
    }
    
    @Bean
    @Throws(SQLException::class)
    open fun embeddedEventStore() : EventStore {
        // TODO replace with a JpaEventStorageEngine
        return EmbeddedEventStore.builder().storageEngine(InMemoryEventStorageEngine()).build()
    }

    @Bean("bookEventSourcingRepository")
    @Throws(SQLException::class)
    open fun eventSourcingRepository() : Repository<BookAggregate> {
        return EventSourcingRepository.builder(BookAggregate::class.java).eventStore(embeddedEventStore()).build();
    }
}