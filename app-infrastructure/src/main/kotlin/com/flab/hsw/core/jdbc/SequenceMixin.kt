package com.flab.hsw.core.jdbc

import com.flab.hsw.core.CoreKopringApplicationImpl.Companion.UNIDENTIFIABLE

interface SequenceMixin {
    val seq: Long

    fun isUnidentifiable(): Boolean {
        return seq == UNIDENTIFIABLE
    }
}
