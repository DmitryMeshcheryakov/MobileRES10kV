package com.graime.streetvoice.screens.base.ui.item

import com.graime.streetvoice.screens.base.ui.adapter.BaseAdapter

class HeaderItem(id: String,
                 title: String,
                 val buttonText: String? = null
): BaseItem(BaseAdapter.VIEW_TYPE_HEADER, id, title)