package com.example.kaamapp.utils

enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}

fun String.toAction():Action{
    return when{
        this=="UPDATE"->Action.UPDATE
        this=="ADD"->Action.ADD
        this=="DELETE"->Action.DELETE
        this=="DELETE_ALL"->Action.DELETE_ALL
        this=="UNDO"->Action.UNDO
        else->Action.NO_ACTION
    }
}