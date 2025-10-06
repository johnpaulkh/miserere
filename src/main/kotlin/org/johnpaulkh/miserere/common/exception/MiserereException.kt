package org.johnpaulkh.miserere.common.exception

class NotFoundException(
    override val message: String,
    private val code: String,
) : RuntimeException(message)