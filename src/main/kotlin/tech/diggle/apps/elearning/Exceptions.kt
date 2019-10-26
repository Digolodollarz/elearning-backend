package tech.diggle.apps.elearning

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidArgumentStateException(override val message: String?) : RuntimeException(message)

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class NullArgumentException(override val message: String?) : RuntimeException(message)