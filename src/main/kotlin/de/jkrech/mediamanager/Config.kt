package de.jkrech.mediamanager

import org.springframework.context.annotation.Configuration
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import java.sql.SQLException
import org.springframework.context.annotation.Bean
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.modelling.command.Repository
import org.axonframework.eventsourcing.EventSourcingRepository
import de.jkrech.mediamanager.domain.Book
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import de.jkrech.mediamanager.application.BookAggregate

@Configuration
open class Config {
    
    @Bean
    @Throws(SQLException::class)
    open fun embeddedEventStore() : EventStore {
        return EmbeddedEventStore.builder().storageEngine(InMemoryEventStorageEngine()).build()
    }

    @Bean
    open fun commandBus() : SimpleCommandBus {
        return SimpleCommandBus.builder().build();
    }

    @Bean
    open fun commandGateway() : DefaultCommandGateway {
        return DefaultCommandGateway.builder().commandBus(commandBus()).build();
    }

    /**
     * Our aggregate root is now created from stream of events and not from a representation in a persistent mechanism,
     * thus we need a repository that can handle the retrieving of our aggregate root from the stream of events.
     *
     * We configure the EventSourcingRepository which does exactly this. We supply it with the event store
     * @return a {@link EventSourcingRepository} implementation of {@link Repository}
     */
    @Bean("bookRepository")
    @Throws(SQLException::class)
    open fun eventSourcingRepository() : Repository<BookAggregate> {
        return EventSourcingRepository.builder(BookAggregate::class.java).eventStore(embeddedEventStore()).build();
    }
}