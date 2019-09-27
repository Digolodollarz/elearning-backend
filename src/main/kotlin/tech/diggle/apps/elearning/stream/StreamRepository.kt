package tech.diggle.apps.elearning.stream

import org.springframework.data.repository.PagingAndSortingRepository

interface StreamRepository: PagingAndSortingRepository<Stream, Long>

interface LevelRepository: PagingAndSortingRepository<Level, Long>

interface ModuleRepository: PagingAndSortingRepository<Module, Long>