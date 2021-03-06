package com.huyingbao.module.github.ui.user.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huyingbao.core.test.fragment.FragmentScenarioRule
import com.huyingbao.module.github.GithubApplication
import com.huyingbao.module.github.R
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class, sdk = [28])
class UserFragmentTest {
    @get:Rule
    var scenarioRule = FragmentScenarioRule(
            UserActivity::class.java,
            UserFragment::class.java,
            null,
            null,
            R.id.fl_container)


    @Before
    fun setUp() {
        scenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun tearDown() {
        scenarioRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun getUserActionCreator() {
        scenarioRule.scenario.onFragment {
            Assert.assertNotNull(it.userActionCreator)
        }
    }

    @Test
    fun getGithubAppStore() {
        scenarioRule.scenario.onFragment {
            Assert.assertNotNull(it.githubAppStore)
        }
    }
}