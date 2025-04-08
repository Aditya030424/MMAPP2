package com.example.mmapp.Counter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private var _counterSavingsButton:MutableLiveData<Int> = MutableLiveData(0)
    val counterSavingsButton:MutableLiveData<Int> = _counterSavingsButton
    private var _counterWishesButton:MutableLiveData<Int> = MutableLiveData(0)
    val counterWishesButton=_counterWishesButton
    private var _countergetIpButton:MutableLiveData<Int> = MutableLiveData(0)
    val countergetIpButton=_countergetIpButton
    private var _counterpostApiButton:MutableLiveData<Int> = MutableLiveData(0)
    val counterpostApiButton=_counterpostApiButton
    private var _counterHome:MutableLiveData<Int> = MutableLiveData(0)
    val counterHome=_counterHome

    private var _counterSendIpButton:MutableLiveData<Int> = MutableLiveData(0)
    val counterSendIpButton=_counterSendIpButton
    private var _countergetIpButton1:MutableLiveData<Int> = MutableLiveData(0)
    val countergetIpButton1 =_countergetIpButton1
    private var _counterBNA:MutableLiveData<Int> = MutableLiveData(0)
    val counterBNA=_counterBNA

    private var _counterPostInfo:MutableLiveData<Int> = MutableLiveData(0)
    val counterPostInfo=_counterPostInfo
    private var _counterGetInfo:MutableLiveData<Int> = MutableLiveData(0)
    val counterGetInfo=_counterGetInfo
    private var _counterTypicode:MutableLiveData<Int> = MutableLiveData(0)
    val counterTypicode=_counterTypicode

    private var _counterAddSavings:MutableLiveData<Int> = MutableLiveData(0)
    val counterAddSavings=_counterAddSavings
    private var _counterShowTotal:MutableLiveData<Int> = MutableLiveData(0)
    val counterShowTotal=_counterShowTotal
    private var _counterSavings:MutableLiveData<Int> = MutableLiveData(0)
    val counterSavings=_counterSavings

    private var _counterAddWishes:MutableLiveData<Int> = MutableLiveData(0)
    val counterAddWishes=_counterAddWishes
    private var _counterShowTotalWishes:MutableLiveData<Int> = MutableLiveData(0)
    val counterShowTotalWishes=_counterShowTotalWishes
    private var _counterClearWishes:MutableLiveData<Int> = MutableLiveData(0)
    val counterClearWishes=_counterClearWishes
    private var _counterWishes:MutableLiveData<Int> = MutableLiveData(0)
    val counterWishes=_counterWishes
    private var _counterPrefButton:MutableLiveData<Int> = MutableLiveData(0)
    val counterPrefButton=_counterPrefButton
    private var _counterCheckBox:MutableLiveData<Int> = MutableLiveData(0)
    val counterCheckBox=_counterCheckBox
    private var _counterSavingsScroll:MutableLiveData<Int> = MutableLiveData(0)
    val counterSavingsScroll:MutableLiveData<Int> = _counterSavingsScroll
    private var _counterWishesScroll:MutableLiveData<Int> = MutableLiveData(0)
    val counterWishesScroll:MutableLiveData<Int> = _counterWishesScroll
}