package org.johnpaulkh.miserere.common.exception

class NotFoundException(
    override val message: String,
    private val code: String,
) : RuntimeException(message)

class MissingParameterException(
    override val message: String,
    private val code: String,
) : RuntimeException(message)

class InvalidParameterException(
    override val message: String,
    private val code: String,
) : RuntimeException(message)
