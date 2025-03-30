package com.pjt.brandpricing.fake

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener

class StubApplicationEventPublisher : ApplicationEventPublisher {
    private val events = mutableListOf<Any>()
    private val listeners = mutableListOf<Any>()

    override fun publishEvent(event: Any) {
        events.add(event)
        listeners.forEach { listener ->
            val methods = listener.javaClass.methods
                .filter { it.isAnnotationPresent(EventListener::class.java) }
            methods.forEach { method ->
                if (method.parameterTypes.firstOrNull()?.isAssignableFrom(event.javaClass) == true) {
                    method.invoke(listener, event)
                }
            }
        }
    }

    fun addListener(listener: Any) {
        listeners.add(listener)
    }

    fun <T> verifyEventPublished(eventType: Class<T>) {
        val exist = events.any { eventType.isInstance(it) }
        check(exist) { "Event ${eventType.simpleName} not published" }
    }
}
