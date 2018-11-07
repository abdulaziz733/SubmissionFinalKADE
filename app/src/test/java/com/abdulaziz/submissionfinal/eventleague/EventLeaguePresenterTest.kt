package com.abdulaziz.submissionfinal.eventleague

import com.abdulaziz.submissionfinal.api.repository.ApiRepository
import com.abdulaziz.submissionfinal.api.repository.ApiRepositoryCallback
import com.abdulaziz.submissionfinal.model.EventLeagueResponse
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq

import org.junit.Before
import org.junit.Test
import org.mockito.*

class EventLeaguePresenterTest {

    @Mock
    private lateinit var view: EventLeagueView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var response: EventLeagueResponse

    private lateinit var presenter: EventLeaguePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventLeaguePresenter(view, apiRepository)

    }

    @Test
    fun getNextEventLoaded() {
        val id = "4328"
        presenter.getDataNextEvents(id)
        argumentCaptor<ApiRepositoryCallback<EventLeagueResponse>>().apply {
            Mockito.verify(apiRepository).getNextMatch(eq(id), capture())
            firstValue.onDataLoaded(response)
        }

        Mockito.verify(view).showLoading()
        Mockito.verify(view).onDataLoaded(response)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getNextEventErrorLoad() {
        val id = ""
        presenter.getDataNextEvents(id)
        argumentCaptor<ApiRepositoryCallback<EventLeagueResponse>>().apply {
            Mockito.verify(apiRepository).getNextMatch(eq(id), capture())
            firstValue.onDataErrorLoad()
        }

        Mockito.verify(view).showLoading()
        Mockito.verify(view).onDataErrorLoad()
        Mockito.verify(view).hideLoading()
    }


}



