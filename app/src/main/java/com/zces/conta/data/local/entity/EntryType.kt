package com.zces.conta.data.local.entity

/**
 * PROJECT_CONTEXT.md #3.8-9: BOQ-linked entry types and general/daily entry types.
 * Stored as a String column (see Converters) so new types can be added without a
 * destructive schema migration; the enum name is the stable on-disk value.
 */
enum class EntryType {
    // BOQ-linked (require a BoqPosition reference)
    EXECUTED_WORK,
    REPAIR_REQUIRED,
    ADDITIONAL_WORK,
    PROBLEM_DELAY,
    DELIVERY,
    HIDDEN_WORK_CONTROL,
    NOTE,

    // General / daily (no BOQ position required)
    WORKFORCE,
    WEATHER,
    DELAY,
    GENERAL_EVENT,
    INSTRUCTION,
    GENERAL_PROBLEM,
    HSE,
    OTHER,
    // Fast capture from the project home action; may later be re-linked to a position (Manual §8)
    QUICK_PHOTO;

    val isBoqLinked: Boolean
        get() = this in setOf(
            EXECUTED_WORK, REPAIR_REQUIRED, ADDITIONAL_WORK,
            PROBLEM_DELAY, DELIVERY, HIDDEN_WORK_CONTROL, NOTE,
        )
}
