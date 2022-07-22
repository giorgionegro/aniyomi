package eu.kanade.presentation.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import eu.kanade.presentation.components.LibraryBottomActionMenu
import eu.kanade.presentation.components.Scaffold
import eu.kanade.presentation.library.components.LibraryContent
import eu.kanade.presentation.library.components.LibraryToolbar
import eu.kanade.tachiyomi.ui.library.LibraryPresenter

@Composable
fun LibraryScreen(
    presenter: LibraryPresenter,
    onMangaClicked: (Long) -> Unit,
    onGlobalSearchClicked: () -> Unit,
    onChangeCategoryClicked: () -> Unit,
    onMarkAsReadClicked: () -> Unit,
    onMarkAsUnreadClicked: () -> Unit,
    onDownloadClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onClickUnselectAll: () -> Unit,
    onClickSelectAll: () -> Unit,
    onClickInvertSelection: () -> Unit,
    onClickFilter: () -> Unit,
    onClickRefresh: () -> Unit,
) {
    Scaffold(
        topBar = {
            val title by presenter.getToolbarTitle()
            LibraryToolbar(
                state = presenter,
                title = title,
                onClickUnselectAll = onClickUnselectAll,
                onClickSelectAll = onClickSelectAll,
                onClickInvertSelection = onClickInvertSelection,
                onClickFilter = onClickFilter,
                onClickRefresh = onClickRefresh,
            )
        },
        bottomBar = {
            LibraryBottomActionMenu(
                visible = presenter.selectionMode,
                onChangeCategoryClicked = onChangeCategoryClicked,
                onMarkAsReadClicked = onMarkAsReadClicked,
                onMarkAsUnreadClicked = onMarkAsUnreadClicked,
                onDownloadClicked = onDownloadClicked,
                onDeleteClicked = onDeleteClicked,
            )
        },
    ) { paddingValues ->
        LibraryContent(
            state = presenter,
            contentPadding = paddingValues,
            currentPage = presenter.activeCategory,
            isLibraryEmpty = presenter.loadedManga.isEmpty(),
            showPageTabs = presenter.tabVisibility,
            showMangaCount = presenter.mangaCountVisibility,
            onChangeCurrentPage = { presenter.activeCategory = it },
            onMangaClicked = onMangaClicked,
            onToggleSelection = { presenter.toggleSelection(it) },
            onRefresh = onClickRefresh,
            onGlobalSearchClicked = onGlobalSearchClicked,
            getNumberOfMangaForCategory = { presenter.getMangaCountForCategory(it) },
            getDisplayModeForPage = { presenter.getDisplayMode(index = it) },
            getColumnsForOrientation = { presenter.getColumnsPreferenceForCurrentOrientation(it) },
            getLibraryForPage = { presenter.getMangaForCategory(page = it) },
            isIncognitoMode = presenter.isIncognitoMode,
            isDownloadOnly = presenter.isDownloadOnly,
        )
    }
}
