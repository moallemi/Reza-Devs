package me.moallemi.composepagingplayground.data

import java.util.UUID

data class SampleData(
  val name: String,
  val id: String = UUID.randomUUID().toString()
)
