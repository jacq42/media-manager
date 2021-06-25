package de.jkrech.mediamanager.config

import de.jkrech.mediamanager.domain.book.Book
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
class AxonConfiguration {

  @Bean
  fun commandBus(): SimpleCommandBus {
    return SimpleCommandBus.builder().build()
  }

  @Bean
  fun commandGateway(): DefaultCommandGateway {
    return DefaultCommandGateway.builder().commandBus(commandBus()).build()
  }

  @Bean
  @Throws(SQLException::class)
  fun embeddedEventStore(): EventStore {
    // TODO replace with a JpaEventStorageEngine
    return EmbeddedEventStore.builder().storageEngine(InMemoryEventStorageEngine()).build()
  }

  @Bean("bookEventSourcingRepository")
  @Throws(SQLException::class)
  fun eventSourcingRepository(): Repository<Book> {
    return EventSourcingRepository.builder(Book::class.java).eventStore(embeddedEventStore()).build()
  }

}