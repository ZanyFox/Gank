package com.zf.jetpackmvvm.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseVBFragment<VB : ViewBinding>(override val isLazy: Boolean = true) :
    LazyFragment(isLazy) {


    protected val mViewBinding
        get() = _binding!!

    private var _binding: VB? = null

    // ViewPager2  会重建
    protected var isNeedLoad = true


    //    abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    @Suppress("UNCHECKED_CAST")
    private fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {

        // 获取带泛型的超类
        val superClass = javaClass.genericSuperclass
        if (superClass is Class<*>)
            throw RuntimeException("不是参数化类型（不带泛型）")
        else {
            // 转换成参数化类型
            val parameterizedType = superClass as ParameterizedType
            // 获取第一个泛型参数
            val clazz: Class<*> = parameterizedType.actualTypeArguments[0] as Class<*>
            // 判断该泛型参数是否是ViewBinding的子类
            if (!ViewBinding::class.java.isAssignableFrom(clazz))
                throw RuntimeException("泛型不是ViewBinding接口")
            else {
                // 反射获取inflate方法
                val inflateMethod = clazz.getDeclaredMethod(
                    "inflate",
                    LayoutInflater::class.java,
                    ViewGroup::class.java,
                    Boolean::class.java
                )
                // 获取ViewBinding对象
                return inflateMethod.invoke(null, inflater, container, false) as VB
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isNeedLoad) {
            super.onViewCreated(view, savedInstanceState)
            isNeedLoad = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding ?: let { _binding = initViewBinding(inflater, container) }
        return mViewBinding.root
    }

}