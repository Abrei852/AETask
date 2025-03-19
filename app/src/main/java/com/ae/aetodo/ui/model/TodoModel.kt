package com.ae.aetodo.ui.model

class TodoModel(title: String, body: String, comment: String, createdAt: String) {
    private var todoTitle: String = title
    private var todoBody: String = body
    private var todoComment: String = comment
    private var todoCreatedAt = createdAt

    fun getTitle(): String {
        return todoTitle
    }

    fun getBody(): String {
        return todoBody
    }
}