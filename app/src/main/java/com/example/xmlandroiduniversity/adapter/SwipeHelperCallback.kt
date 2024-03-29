package com.example.xmlandroiduniversity.adapter

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.xmlandroiduniversity.R
import kotlin.math.min

class SwipeHelperCallback(private val recyclerViewAdapter: PoiAdapter) : ItemTouchHelper.Callback() {
    
    // swipe_view 를 swipe 했을 때 <삭제> 화면이 보이도록 고정하기 위한 변수들
    private var currentPosition: Int? = null // 현재 선택된 recycler view 의 position
    private var previousPosition: Int? = null // 이전에 선택했던 recycler view의 position
    private var currentDx = 0f // 현재 x 값
    private var clamp = 0f // 고정시킬 크기

    // 이동 방향 결정하기
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // 드래그 방향: 위, 아래 인식
        // Swipe 방향: 왼쪽, 오른쪽 인식
        // 설정 안하고 싶으면 0
        return makeMovementFlags(UP or DOWN, LEFT or RIGHT)
    }

    // 드래그 일어날 때 동작 (롱터치 후 드래그)
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // recyclerView 에서 현재 선택된 데이터와 드래그한 위치에 있는 데이터를 교환
        val fromPos: Int = viewHolder.adapterPosition
        val toPos: Int = target.adapterPosition
        recyclerViewAdapter.swapData(fromPos, toPos)
        return true
    }

    // 스와이프 일어날 때 동작
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Swipe 끝까지 하면 해당 데이터 삭제하기 -> Swipe 후 <삭제> 버튼 눌러야 삭제 되도록 변경
        recyclerViewAdapter.removeData(viewHolder.layoutPosition)
    }

    // --------------- Swipe 됐을 때 일어날 동작 ---------------
    // swipe_view 만 슬라이드 되도록 + 일정 범위를 swipe 하면 <삭제> 화면 보이게 하기

    // 사용자와의 상호작용과 해당 애니메이션도 끝났을 때 호출
    // drag 된  view 가 drop 되었거나, swipe 가 cancel 되거나 complete 되었을 때 호출
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        currentDx = 0f  // 현재 x 위치 초기화
        previousPosition = viewHolder.adapterPosition // 드래그 또는 스와이프 동작이 끝난 view 의 position 기억하기
        getDefaultUIUtil().clearView(getView(viewHolder))
    }

    // ItemTouchHelper 가 ViewHolder 를 swipe 되었거나 드래그 되었을 때 호출
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            currentPosition = viewHolder.adapterPosition  // 현재 드래그 또는 swipe 중인 view 의 position 기억하기
            getDefaultUIUtil().onSelected(getView(it))
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ACTION_STATE_SWIPE) {
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)  // 고정할지 말지 결정, true: 고정함 false: 고정 안함
            val newX = clampViewPositionHorizontal(dX, isClamped, isCurrentlyActive)  // newX 만큼 이동 (고정 시 이동 위치/ 고정 해제시 이동 위치 결정)

            // 고정시킬 시 애니메이션 추가
            if (newX == -clamp) {
                getView(viewHolder).animate().translationX(-clamp).setDuration(100L).start()
                return
            }

            currentDx = newX

            view.translationX = newX

            getDefaultUIUtil().onDraw(
                c, recyclerView, view, newX, dY, actionState, isCurrentlyActive
            )
        }
    }

    // 사용자가 view 를 swipe 했다고 간주할 최소 속도 정하기
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float = defaultValue * 10

    // 사용자가 view 를 swipe 했다고 간주하기 위해 이동해야하는 부분 반환
    // 사용자가 손을 떼면 호출됨
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        // -clamp 이상 swipe 시 isClamped를 true 로 변경 // 아닐시 false 로 변경
        setTag(viewHolder, currentDx <= -clamp)
        return 2f
    }

    // swipe_view 반환 -> swipe_view 만 이동할 수 있게해줌
    private fun getView(viewHolder: RecyclerView.ViewHolder) : View = viewHolder.itemView.findViewById(R.id.swipeView)

    // swipe_view 를 swipe 했을 때 <삭제> 화면이 보이도록 고정
    private fun clampViewPositionHorizontal(dX: Float, isClamped: Boolean, isCurrentlyActive: Boolean) : Float {
        // RIGHT 방향으로 swipe 막기
        val max = 0f

        // 고정할 수 있으면
        // 고정할 수 있으면
        val newX = if (isClamped) {
            // 현재 swipe 중이면 swipe되는 영역 제한
            if (isCurrentlyActive)
            // 오른쪽 swipe일 때
                if (dX < 0) dX/3 - clamp
                // 왼쪽 swipe일 때
                else dX - clamp
            // swipe 중이 아니면 고정시키기
            else -clamp
        }
        // 고정할 수 없으면 newX 는 swipe 한 만큼
        else dX / 2

        // newX 가 0 보다 작은지 확인
        return min(newX, max)
    }

    // isClamped 를 view 의 tag 로 관리
    // isClamped = true : 고정 , false : 고정 해제
    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) { viewHolder.itemView.tag = isClamped}
    private fun getTag(viewHolder: RecyclerView.ViewHolder) : Boolean = viewHolder.itemView.tag as? Boolean ?: false

    // view 가 swipe 되었을 때 고정될 크기 설정
    fun setClamp(clamp: Float) { this.clamp = clamp }

    // 다른 View 가 swipe 되거나 터치되면 고정해제
    fun removePreviousClamp(recyclerView: RecyclerView) {
        // 현재 선택한 view가 이전에 선택한 view와 같으면 패스
        if (currentPosition == previousPosition) return

        // 이전에 선택한 위치의 view 고정 해제
        previousPosition?.let {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            getView(viewHolder).animate().x(0f).setDuration(100L).start()
            setTag(viewHolder, false)
            previousPosition = null
        }
    }
}