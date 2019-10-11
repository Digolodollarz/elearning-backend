package tech.diggle.apps.elearning.chat

import org.springframework.data.repository.PagingAndSortingRepository

interface ChatMessageRepository : PagingAndSortingRepository<ChatMessage, Long>

interface ChatRoomRepository : PagingAndSortingRepository<ChatRoom, Long>

interface GroupMessageRepository : PagingAndSortingRepository<GroupMessage, Long>{
    fun findByRoomId(id: Long): List<GroupMessage>
}

interface DMRepository : PagingAndSortingRepository<DM, Long>
