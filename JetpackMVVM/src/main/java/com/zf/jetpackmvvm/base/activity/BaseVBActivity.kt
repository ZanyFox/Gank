package com.zf.jetpackmvvm.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract  class BaseVBActivity<VB : ViewBinding> : BaseActivity() {

    // protect 子类可见
    protected val mViewBinding: VB by lazy { initViewBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        preOnCreate()
        super.onCreate(savedInstanceState)
        preSetContentView()
        setContentView(mViewBinding.root)
        initializeView()
        preInit()
        initialize()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initViewBinding(): VB {

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
                val inflateMethod = clazz.getDeclaredMethod("inflate", LayoutInflater::class.java)
                // 获取ViewBinding对象
                return inflateMethod.invoke(null, layoutInflater) as VB
            }
        }
    }

    protected open fun preOnCreate() {}

    protected open fun preSetContentView() {}

    protected open fun preInit() {

    }
}