package com.shevelev.mywinningstreaks.coreentities.enums

enum class Status {
    /**
     * When a user achieves a goal
     */
    Marked,

    /**
     * When a user doesn't achieve a goal
     */
    Skipped,

    /**
     * When a user doesn't achieve a goal but he has a valid reason
     */
    Sick,

    Unknown,
}