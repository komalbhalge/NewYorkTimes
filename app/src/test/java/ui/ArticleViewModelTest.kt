package ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kb.nytimes.domain.usecase.FetchMostEmailedArticlesUseCase
import com.kb.nytimes.domain.usecase.FetchMostSharedArticlesUseCase
import com.kb.nytimes.domain.usecase.FetchMostViewedArticlesUseCase
import com.kb.nytimes.domain.usecase.base.BaseArticlesUseCase
import com.kb.nytimes.data.model.Article
import com.kb.nytimes.data.model.MostPopularArticlesResponse
import com.kb.nytimes.testutils.thenReturnFlow
import com.kb.nytimes.ui.ArticlesViewModel
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ArticleViewModelTest {

    lateinit var viewModel: ArticlesViewModel

    @Mock
    lateinit var fetchMostViewedArticlesUseCase: FetchMostViewedArticlesUseCase

    @Mock
    lateinit var fetchMostSharedArticlesUseCase: FetchMostSharedArticlesUseCase

    @Mock
    lateinit var fetchMostEmailedArticlesUseCase: FetchMostEmailedArticlesUseCase

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = ArticlesViewModel(
            fetchMostViewedArticlesUseCase = fetchMostViewedArticlesUseCase,
            fetchMostSharedArticlesUseCase = fetchMostSharedArticlesUseCase,
            fetchMostEmailedArticlesUseCase = fetchMostEmailedArticlesUseCase
        )
        setupObservers()
    }

    private fun setupObservers() {
        with(viewModel) {
            articlesList.observeForever { }
            toolbarLabel.observeForever { }
        }
    }

    @Test
    fun `should get data from most viewed articles api`() {
        val mostViewedResponse = createMockViewedArticlesResponse()
        whenever(fetchMostViewedArticlesUseCase.build(createMockParams())).thenReturnFlow(
            mostViewedResponse
        )

        with(viewModel) {
            loadArticles("viewed", 1)
            Assert.assertEquals(2, articlesList?.value?.results?.size)
        }
    }

    private fun createMockParams() = BaseArticlesUseCase.Params(period = 7)

    private fun createMockViewedArticlesResponse() =
        MostPopularArticlesResponse(
            copyright = "String",
            num_results = 1,
            results = listOf(
                Article(
                    uri = "nyt://article/fcde30a2-f609-531f-b5c9-8e7df51a2f44",
                    url = "https://www.nytimes.com/2021/07/24/technology/joseph-mercola-coronavirus-misinformation-online.html",
                    id = 100000007878607,
                    asset_id = 100000007878607,
                    source = "New York Times",
                    published_date = "2021-07-24",
                    updated = "2021-07-25 09:57:34",
                    section = "Technology",
                    subsection = "",
                    title = "The Most Influential Spreader of Coronavirus Misinformation Online",
                ),
                Article(
                    uri = "nyt://article/fcde30a2-f609-531f-b5c9-8e7df51a2f44",
                    url = "https://www.nytimes.com/2021/07/24/technology/joseph-mercola-coronavirus-misinformation-online.html",
                    id = 100000007878607,
                    asset_id = 100000007878607,
                    source = "New York Times",
                    published_date = "2021-07-24",
                    updated = "2021-07-21 09:57:34",
                    section = "Technology",
                    subsection = "",
                    title = "Best day",
                )
            ),
            status = "status"
        )

}