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