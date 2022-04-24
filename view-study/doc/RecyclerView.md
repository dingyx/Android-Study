## RecyclerView 

> 为**有限**的屏幕显示**大量**的数据的**灵活**的 View

![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/RecyclerView_是什么.png)

### ListView 局限

* 只支持纵向列表布局

* 没有支持动画的 API

* 接口设计和系统不一致

  * setOnItemClickListener()

  * setOnItemLongClickListener()

  * setSelection()

    > 与系统 View 的相关接口重复

  * 没有强制实现 ViewHolder

  * 性能不如 RecyclerView

### RecyclerView 优势

* 默认支持 Linear、Grid、Staggered Gird 三种布局

* 友好的 ItemAnimator 动画 API

* 强制实现 ViewHolder

* 解耦的架构设计

* 相比 ListView 更好的性能

  ![LayoutManager 支持的布局](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/RecyclerView_支持的布局.png)

### RecyclerView 的重要组件

​	![RecyclerView 的重要组件](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/RecyclerView的重要组件.png)

### View holder 究竟是什么

* View holder 和 item view 是什么关系

* View holder 解决的是什么问题

* View holder 和 ListView item view 的复用有什么关系

  * 没有实现 view holder 的 getView()

    > 不管 convertView 是否为空，需要重复 findViewById 

    ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/没有实现viewholder的getView().png)

    > **不实现 view holder** 还是会 **复用 item view**

  * 实现了 view holder 的 getView()

    > ViewHolder 是保存 View 引用的**容器**类

    > 每次创建 convertView时，创建一个ViewHolder对象， 将 convertView和它的子View 都存入 ViewHolder对象。

    > 将convertView、viewHolder 通过 setTag 绑定。 **View 的 setTag方法，可以存入一个 Object 对象**

    ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/实现viewholder的getView().png)

* item view 和 view holder 一一对应

  ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/itemview和viewholder一一对应.png)

* view holder 解决重复 findViewById 来提高效率

  > 不用 view holder 一样也会复用 item view ，只是 findViewById 比较耗性能

  View Holder 最佳实践

  ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/ViewHolder最佳实践.png)

  > 具体绑定逻辑 写入对应 ViewHolder ，ViewHolder 声明 bindTo 方法。

### RecyclerView 缓存机制

* ListView 缓存原理

  ![ListView 缓存示图一](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/ListView缓存示图一.png)

  * RecyclerBin

    > 管理 ListView 缓存 (item view)

    > active view --- scrap view --- create view

  * Active View

    > 屏幕里面的 item view

  * Scrap View

    > 移出屏幕的 item view

  * Create View

  ![ListView 缓存示图二](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/ListView缓存示图二.png)

  > 直接复用，不需要重新绑定数据的 item view，会跳过 getView 调用
  >
  > 调用了 getView 的，都需要进行数据绑定的操作。

* RecyclerView 缓存原理

  

  ![RecyclerView 缓存示图一](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/RecyclerView缓存示图一.png)

  * Recycler 

    > 管理 RecyclerView 缓存 (viewHolder)

    > ListView 缓存的是 **itemView** 对象，RecyclerView 缓存的是 **viewHolder**（ViewHolder持有itemView，相当于也缓存了itemView。）

  * Scrap

    > 屏幕内部的 item view

  * Cache

    > 移出屏幕的 item view

    > Scrap、Cache 直接复用，不需要绑定数据

  * ViewCacheExtension

    > 用户自定义缓存策略

  * RecyclerViewPool

    > 缓存池

    ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/RecyclerView缓存示图二.png)

### ViewCacheExtension Example

* 广告卡片
  * 每一页一共4个广告
  * 这些广告短期内不会发生变化
* 每次滑入一个广告卡片，一般情况下都需要重新绑定
* Cache 只关心 Position ，不关心 view type
* RecyclerViewPool 只关心 view type，都需要重新绑定
* 在 ViewCacheExtension 里保持4个广告

### 注意：列表中 item/广告的 impression 统计

* ListView 通过 getView() 统计
* RecyclerView 通过 onBindViewHolder 统计？<font color=red>可能错误！</font> 
* 通过 onViewAttachedToWindow() 统计



## RecyclerView 性能优化策略

### 在 onBindViewHolder 里设置点击监听？

> onBindViewHolder 里设置点击监听器会导致**重复**创建对象

![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/在onBindViewHolder里设置点击监听.png)

### 在 onCreateViewHolder 里设置点击监听！

> View - ViewHolder - View.OnClickListener 三者一一对应

![在onCreateViewHolder里设置点击监听](C:\Users\admin020\Desktop\在onCreateViewHolder里设置点击监听.png)

### LinearLayoutManager.setInitialPrefetchItemCount()

![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/LinearLayoutManager.setInitialPrefetchItemCount().png)

> 使用 LinearLayoutManager.setInitialPrefetchItemCount()

* 用户滑动到横向滑动的 item RecyclerView 的时候，由于需要创建更复杂的 RecyclerView 以及多个子 View ，可能会导致页面卡顿
* 由于 RenderThread 的存在，RecyclerView 会进行 prefetch
* LinearLayoutManager.setInitialPrefetchItemCount(横向列表初次显示时可见的 item 个数)
  * 只有 LinearLayoutManager 有这个 API
  * 只有嵌套在内部的 RecyclerView 才会生效

### RecyclerView.setHasFixedSize()

![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/RecyclerView.setHasFixedSize().png)

> 如果 Adapter 的数据变化不会导致 RecyclerView 的大小变化

```java
RecyclerView.setHasFixedSize(true);
```

### 多个 RecyclerView 共用 RecycledViewPool

![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/多个RecyclerView共用RecycledViewPool.png)

![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/共用RecyclerViewPool代码.png)

### DiffUtil

* DiffUtil is a utility class that <font color=blue>calculates the difference between two lists</font> and <font color=green>outputs a list of update operations</font> that <font color="#GGFF00">converts the first list into the second one</font>.

* 局部更新方法 notifyItemXXX() 不适用于所有情况

* notifyDataSetChange() 会导致整个布局重绘，重新绑定所有 ViewHolder，而且会失去可能的动画效果。

  > 删除、移动item，RecyclerView会做相应动画。 如果直接notifyDataSetChange不会有动画。

* DiffUtil 适用于整个页面需要刷新，但是有部分数据可能相同的情况。

  * 算法原理 [https://blog.robertelder.org/diff-algorithm](https://blog.robertelder.org/diff-algorithm)

    ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/MyersDiffAlgorithm.png)

  * DiffUtil CallBack 源码

    ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/DiffUtil的CallBack.png)

    ![DiffUtil.CallBack逻辑](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/DiffUtil.CallBack逻辑.png)

  * DiffUtil Callback Demo

    ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/DiffUtil.CallBack的Demo.png)

    > 不实现  getChangePayload ，没有增量更新。会复用整个 item

    ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/DiffUtil.CallBack的Demo_getChangePayload.png)

    > swapData
    
    ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/RecyclerView_Adapter_swapData.png)
    
    > 全量更新
    >
    > ```
    > onBindViewHolder(@NonNull DemoViewHolder holder, int position)
    > ```
    >
    > 增量更新
    >
    > ```
    > onBindViewHolder(@NonNull DemoViewHolder holder, int position, @NonNull List<Object> payloads)
    > ```
    
    ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/onBindViewHolder_payloads.png)
  
* DiffUtil 效率

  ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/DiffUtil效率.png)

* 在列表很大的时候异步计算 diff

  > 耗时操作放到异步线程

  * 使用 Thread/Handler 将 DiffResult 发送到主线程

  * 使用 RxJava 将 calculateDiff 操作放到后台线程

  * 使用 Google 提供 AsyncListDiff(Excutor)/ListAdapter

    > * [https://developer.android.google.cn/reference/androidx/recyclerview/widget/AsyncListDiffer](https://developer.android.google.cn/reference/androidx/recyclerview/widget/AsyncListDiffer)
    > * [https://developer.android.google.cn/reference/androidx/recyclerview/widget/ListAdapter](https://developer.android.google.cn/reference/androidx/recyclerview/widget/ListAdapter)

## ItemDecoration 

### 为什么 ItemDecoration 可以绘制分隔线？

* ItemDecoration 源码

  ```java
  public abstract static class ItemDecoration {
  
      // item view 绘制前绘制，会出现在 item view 下方位置
      public void onDraw(Canvas c, RecyclerView parent, State state) {
          onDraw(c, parent);
      }
  
      // item view 上位置，覆盖在 item view 上
      public void onDrawOver(Canvas c, RecyclerView parent, State state) {
          onDrawOver(c, parent);
      }
  
  
      // 偏移
      public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
          getItemOffsets(outRect, ((LayoutParams) view.getLayoutParams()).getViewLayoutPosition(),
                         parent);
      }
      
  }
  ```

* DividerItemDecoration (Google API 系统分隔线实现)

  > 重写 onDraw 实现绘制  在 item下方绘制

![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/InsetDivider.png)

* Overlay Divier

  > 在 item 内绘制

  ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/OverlayDivider.png)

  * 实现方法

    ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/OverlayDivider代码.png)

### ItemDecoration 还可以做什么？

* Drawing dividers between items

* Highlights

  ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/ItemDecoration_highlights.png)

* Visual grouping boundaries

  ![](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/Visual_grouping_boundaries.png)

## RecyclerView 其他知识点

* [https://github.com/h6ah4i/android-advancedrecyclerview](https://github.com/h6ah4i/android-advancedrecyclerview)