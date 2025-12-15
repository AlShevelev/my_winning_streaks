package com.shevelev.mywinningstreaks.coreentities

enum class Status {
    /**
     * When a user achieves a goal
     */
    Won,

    /**
     * When a user doesn't achieve a goal
     */
    Failed,

    /**
     * When a user doesn't achieve a goal but he has a valid reason
     */
    Sick,

    Unknown,
}