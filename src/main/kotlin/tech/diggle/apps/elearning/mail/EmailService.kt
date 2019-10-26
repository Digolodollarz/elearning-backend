package tech.diggle.apps.elearning.mail

interface EmailService {
    fun sendSimpleMessage(to: String, subject: String, text: String)
}