package com.umut.selamandroid

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import com.umut.selamandroid.ui.main.MainFragment


/**
 *      Activity state and ejection from memory konusuna bak. (Activityler hangi durumda ne siklikla
 *      destroy olmaya zorlanir bilgisi)
 *      Handling LifeCycles with Lifecycle-Aware Components konusuna bak (kendi olusturdugun
 *      classin activity lifecycle'indan haberdar olmasi ozelligi).
 */
class MainActivity : AppCompatActivity() {

    val edittext: EditText by lazy {
        findViewById(R.id.editTextText)
    }
    /**
     *      Datayla ve viewi ayaga kaldirmakla ilgili tum kodlar burada yer alir.
     *      Activity ekranda gorunmeden once ilk initial islemi sirasinda sistem tarafindan
     *      ilk ayaga kalkan yerdir. Islerin cogunlugu burada yapilir.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("Main Activity", "onCreate1")

        setContentView(R.layout.activity_main)

        Log.e("Main Activity", "onCreate2")
        /*if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }*/
    }

    /**
     *      Bu asamada artik activity kullaniciya gorunur. Ekrandadir. on -Start ve onResume
     *      kisminda onStop veya onPause evrelerinde geri donusde elde tutulacak olan
     *      veya resetlenecek olan icerideki datalarin sifirlandigi reset edildigi release edildigi
     *      destroy ettigimiz yerdir.
     *
     *      Stopta yapilan seylerin tersi startta yapilir.
     *
     *      Ekranin geldigi noktadir.
     *
     */
    override fun onStart() {
        super.onStart()
        Log.e("Main Activity", "onStart")

    }

    /**
     *      onStartta artik kullaniciya gorunen uygulama calisir durumdayken focusu ilgili
     *      activitynin uzerinde tutuyorken bulunulan yerdir. Bundan sonra artik kullanici
     *      activityi kullanir.
     *
     *      pauseta yapilan seylerin tersi resumeda yapilir. Sebebini activity lifecycle
     *      diyagramina bakarak anlayabilirsin.
     *
     *      onResumeda data cekme istegi atilmamalidir. Gereken durumlarda yapilabilir ama yapilmamasi
     *      gereken yerde yapilmamlidir. Bazi yerlerde lazimda olacaktir. Surekli data yenilenmesi
     *      gereken yapilar. (Borsa ornek)
     */
    override fun onResume() {
        super.onResume()
        Log.e("Main Activity", "onResume")

    }

    /**
     *      Pop up ekran ciktiginda activity pausea duser. multiwindow kullanmimda ui'i kullanici
     *      gorebilir.
     *      Focus kayboldugu anda pause moduna gecilir.
     *
     *      Release, nullama, close gibi islemler yapilirsa cok zaman kullanmasi gerekn bir sey
     *      varsa bu onPauseda degil onStopta yapilmalidir. onPauseda yapilirsa app not responding
     *      hatasi alinabilir. App ekrani donar.
     */
    override fun onPause() {
        super.onPause()
        Log.e("Main Activity", "onPause")

    }

    /**
     *      ui'in artik kullaniciya gozukmedigi evredir. Multiwindow bi sekilde kullanimda da
     *      ui artik kullaniciya gozukmeyecektir.
     *      Ekranin gittigi noktadir.
     */
    override fun onStop() {
        super.onStop()
        Log.e("Main Activity", "onStop")

    }

    /**
     *      onDestroy evresinde artik activity artik kaybolur. Memoryden silinmeye hazir hale
     *      gelmistir. Tamamemen silinmemistir ama garbage collector bu activityi silecektir.
     *
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.e("Main Activity", "onDestroy")

    }

    override fun onRestart() {
        super.onRestart()
        Log.e("Main Activity", "onRestart")

    }

    /**
     *      Cozum olarak edittexte id koymak yeterlidir fakat is mulakatlarinda sorulan
     *      cozum asagidaki gibi edittext icerisindeki texti onSaveInstance ile tutup
     *      uygulama destroy edilip bşr daha createlendiginde onRestoreInstance icerisinde
     *      tekrardan edittexte vermek olacaktir. onRestore yerine onCreate icerisinde de yapilabilir.
     *      Incelersen gorursun.
     *
     *      Telefonun text size'i, dili, orientation, text stilli, uygulama boyutlandirmasi gibi
     *      durumlarin hepsinde mevcut activity oldurulur ve activity tekrardan create edilir.
     *      Yapilan degisikleri tutmak icin asagidaki ornek yapilmalidir.
     *
     *      onStopta veri release veya reset veya null yapilacaksa burada dikkat edilmesi gerekir.
     *      onStoptan sonra onSaveInstance calisir. Bu durumda onStopta kaybedilmek istenmeyen
     *      veriler saklanmalidir.
     *
     *      Guncel olarak sirketlerde artik saveInstance ve restoreInstance neredeyse hic
     *      kullanilmamaktadir. Bunun sebebi bu problemin viewModellar ile de cozülebilmesidir.
     */
    override fun onSaveInstanceState(outState: Bundle) {

        val name = edittext.text.toString()

        outState.run {
            putString("keyName", name)
        }

        super.onSaveInstanceState(outState)
        Log.e("Main Activity", "onSaveInstanceState")

    }

    /**
     *      Landscapeten portraite gecerken veya tam tersi (configuration change ornegi) uygulama onCreate ve onStart
     *      olduktan sonra direkt onResume olmak yerine once onRestoreInstanceState'e girer.
     *
     *      Edittexte id atayarak ekranin landscape portrait arasi donusumlerinde textin
     *      resetlenmesi sorununu cozebiliriz.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val name = savedInstanceState.getString("keyName")
        edittext.setText(name)


        Log.e("Main Activity", "onRestoreInstanceState")

    }

    /**
     *      Kullanici her ekrana tikladiginda bu fonksiyon calisir.
     */
    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.e("Main Activity", "onUserInteraction")

    }


    /**
     *      Kullanici ortadaki tus (home) a tikladiginda burasi calisir.
     */
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        Log.e("Main Activity", "onUserLeaveHint")

    }

    var isBackPressed = true
    private var mLastClickTime = 0L


    /**
     *      Android 12 ve ustunde backPress eger main activity'ye yapilirsa bu main activityi
     *      oldurmez. onStop'a alir. App arka planda calismaya devam eder. Bu sadece main activity
     *      icin gecerlidir.
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 10000){ // double click blocker
            isBackPressed = true
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if (isBackPressed) {
//            "Are you sure to leave the app?" message this
            this message "Are you sure to leave the app?"
            isBackPressed = false
        } else {
            super.onBackPressed()

        }
        Log.e("Main Activity", "onBackPressed")

    }

    /*override fun onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            println("Back button pressed")
            // Code that you need to execute on back press i.e. finish()
        }
    })*/

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.e("Main Activity", "onConfigurationChanged")

    }

    override fun setContentView(layoutResID: Int) {
        Log.e("Main Activity", "setContentView")
        super.setContentView(layoutResID)

    }

    override fun onOptionsMenuClosed(menu: Menu?) {
        super.onOptionsMenuClosed(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     *      onBackPressed icerisinde kullanilmak icin yazilmistir.
     *      Cikmadan once emin misin diye bir pop up.
     */
    infix fun String?.message(context: Context) {
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }

    infix fun Context?.message(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}


