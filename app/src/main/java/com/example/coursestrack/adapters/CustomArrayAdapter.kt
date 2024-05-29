package com.example.coursestrack.adapters

import android.content.Context
import android.widget.ArrayAdapter

class CustomArrayAdapter(context: Context, resource: Int, items: Array<String>) :
    ArrayAdapter<String>(context, resource, items)